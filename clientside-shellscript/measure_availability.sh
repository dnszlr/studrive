#!/bin/bash
#title:	measure_availability.sh
#description:	Used to determine the impact of patterns for microservices on availability
#		Measures via curl data from outgoing requests and stores the data in a .csv file to be able to import them later 			to excel
#author:	Dennis Zeller
#date:		18/01/2022
#version:	0.3
#usage:	Start this file with command ./measure_availability [url] [export_file_name.csv]
#notes:	Use curl version 7.72.0 to get the best results.  
#credit:	Inspired by https://ops.tips/gists/measuring-http-response-times-curl/
#os:		Implemented for Linux
#note:		Use netem to emulate a malfunction with packet loss https://wiki.linuxfoundation.org/networking/netem

echo 'Start script'

main() {
	local url=$1
	local file=$2
	
	
printHeader $file
for value in {1..100}
do
	makeRequest $url $file
	printf "######## Request %d/100 ########\r" $value
	sleep 2s
done
}

printHeader() {
	echo "timestamp,method,url,status,curl_exit" >> $file
}

makeRequest() {
	local url=$1
	curl \
	--write-out "$(date "+%d.%m.%Y %H:%M:%S"),%{method},%{url_effective},%{http_code}," >> $file \
	--max-time 5.0 \
	--silent \
	--output /dev/null \
	"$url"
	res=$?
	echo "$res" >> $file
}
main "$@"
