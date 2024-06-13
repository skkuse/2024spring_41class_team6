#!/bin/bash

get_cpu_usage() {
    pid=$1
    total_time=$(awk '{print $14+$15}' /proc/$pid/stat)
    uptime=$(grep 'cpu ' /proc/stat | awk '{print $1+$2+$3+$4+$5+$6+$7+$8}')
    cpu_usage=$(awk "BEGIN {print ($total_time / $uptime) * 100}")
    echo "$cpu_usage"
}

get_memory_usage() {
    pid=$1
    memory_usage=$(awk '/VmRSS/{print $2}' /proc/$pid/status)
    echo "$memory_usage"
}

javac Temp.java



start_time=$(date +%s%3N)

PIDFILE=pid.txt
java Temp > java_output.txt 2>&1 &
JAVA_PID=$!
echo $JAVA_PID > $PIDFILE

# Monitor CPU and memory usage while the Java process is running
while kill -0 $JAVA_PID 2>/dev/null; do
    cpu_usage=$(get_cpu_usage $JAVA_PID)
    memory_usage=$(get_memory_usage $JAVA_PID)
#    echo "CPU usage: $cpu_usage%, Memory usage: $memory_usage kB"
    echo "$cpu_usage"
    echo "$memory_usage"
    sleep 1
done



end_time=$(date +%s%3N)

duration=$((end_time - start_time))

java_output=$(cat java_output.txt)

echo "$duration"
echo "$java_output"
