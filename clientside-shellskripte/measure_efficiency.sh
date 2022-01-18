#!/bin/bash
#title:	      	measure_efficiency.sh
#description:	  Used to determine the impact of patterns for microservices on performance efficiency
#		            Measures via curl data from outgoing requests and stores the data in a .csv file to be able to import them later to excel
#author:	      Dennis Zeller
#date:		      17/01/2022
#version:	      0.2
#usage:	      	./measure_efficiency.sh [url] [export_file_name.csv]
#notes:	      	Use curl version 7.72.0 to get the best results.
#credit:      	Inspired by https://ops.tips/gists/measuring-http-response-times-curl/
#os:		        Implemented for Linux

echo 'Start script'

# Emulate delay in latency in local network to
# simulate real network traffic
# Remove current delay if exists
tc qdisc del dev ens160 root
# --> Replace [ens160] with your network.
echo 'Set local network delay to 200ms'
tc qdisc add dev ens160 root netem delay 200ms

main() {
	local url=$1
	local file=$2
	
	
printHeader $file
for value in {1..100}
do
	makeRequest $url $file
	printf "######## Request %d/100 ########\r" $value
	sleep 5s
done
}

printHeader() {
	echo "timestamp,method,url,status,connect_time,total_time" >> $file
}

makeRequest() {
	local url=$1
	curl \
	--write-out "$(date "+%d.%m.%Y %H:%M:%S"),%{method},%{url_effective},%{http_code},%{time_connect},%{time_total}\n" >> $file \
	--silent \
	--output /dev/null \
	"$url"
}
main "$@"

# Remove delay in latency in local network
# --> Replace [ens160] with your network again.
echo 'Remove local network delay'
tc qdisc del dev ens160 root
