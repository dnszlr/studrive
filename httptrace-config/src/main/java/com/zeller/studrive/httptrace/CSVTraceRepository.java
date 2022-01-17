package com.zeller.studrive.httptrace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import java.io.*;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CSVTraceRepository implements HttpTraceRepository {

	@Value("${spring.application.name}")
	private String appName;
	private final Logger logger = LoggerFactory.getLogger(CSVTraceRepository.class);
	private final String comma = ",";
	private AtomicReference<HttpTrace> lastTrace = new AtomicReference<>();

	@Override
	public List<HttpTrace> findAll() {
		return Collections.singletonList(lastTrace.get());
	}

	@Override
	public void add(HttpTrace trace) {
		if(checkUri(trace.getRequest().getUri())) {
			lastTrace.set(trace);
			writeToCSV(trace);
		}
	}

	private void writeToCSV(HttpTrace trace) {
		File directory = new File("./data/csv/");
		if(!directory.exists()) {
			directory.mkdirs();
		}
		File file = new File(directory + "/" + appName + "Dump.csv");
		StringBuilder sb = !file.isFile() ? csvHeader() : new StringBuilder();
		try(PrintWriter writer = new PrintWriter(new FileWriter(file.getAbsolutePath(), true))) {
			sb.append(trace.getTimestamp());
			sb.append(comma);
			sb.append(trace.getRequest().getMethod());
			sb.append(comma);
			sb.append(trace.getRequest().getUri());
			sb.append(comma);
			sb.append(trace.getResponse().getStatus());
			sb.append(comma);
			sb.append(trace.getTimeTaken());
			sb.append('\n');
			writer.write(sb.toString());
		} catch(FileNotFoundException e) {
			logger.debug(e.getMessage());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private StringBuilder csvHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append("timestamp");
		sb.append(comma);
		sb.append("request method");
		sb.append(comma);
		sb.append("request uri");
		sb.append(comma);
		sb.append("response status");
		sb.append(comma);
		sb.append("time taken");
		sb.append('\n');
		return sb;
	}

	private boolean checkUri(URI uri) {
		return uri != null && uri.getPath().contains("v1");
	}
}
