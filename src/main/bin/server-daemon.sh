#!/usr/bin/env bash
bin=`dirname $0`
bin=`cd "$bin"; pwd`
PROJECT_NAME="yugui"
DEFAULT_CONF_DIR="$bin"/../conf
DEFAULT_HOME=`cd "$bin"/..;pwd`
CONF_DIR=${CONF_DIR:-$DEFAULT_CONF_DIR}
PROJECT_HOME=${PROJECT_HOME:-$DEFAULT_HOME}
LOG_DIR=${LOG_DIR:-$PROJECT_HOME/logs}
PID_FILE="$LOG_DIR/.${PROJECT_NAME}.run.pid"
HEAP_OPTS="-Xmx2048m -Xms2048m"
mkdir -p ${LOG_DIR}

function running(){
	if [ -f "$PID_FILE" ]; then
		pid=$(cat "$PID_FILE")
		process=`ps aux | grep " $pid " | grep -v grep`;
		if [ "$process" == "" ]; then
	    		return 1;
		else
			return 0;
		fi
	else
		return 1
	fi
}

function start_server() {
	if running; then
		echo "is running."
		exit 1
	fi
        if [ -n "${JAVA_HOME}" ]; then
    	  RUNNER="${JAVA_HOME}/bin/java"
	else
    	  if [ `command -v java` ]; then
            RUNNER="java"
    	  else
            echo "JAVA_HOME is not set" >&2
            exit 1
     	  fi
    fi

	nohup java -Xmx512m -Xms512m  -cp "$CLASSPATH"   -Dlog.dir=`pwd`/../logs  -Dlogfile.name=yugui.log -jar $DEFAULT_HOME/lib/yugui-1.0.jar "$@" > "$LOG_DIR"/"$PROJECT_NAME".out 2>&1 < /dev/null &
        echo $! > $PID_FILE
        chmod 755 $PID_FILE
	echo "start ${PROJECT_NAME} successfully."
}

function stop_server() {
	if ! running; then
		echo "${PROJECT_NAME} is not running."
		#exit 1
	fi
	count=0
	pid=$(cat $PID_FILE)
	while running;
	do
	  let count=$count+1
	  echo "Stopping $count times"
	  if [ $count -gt 5 ]; then
	      echo "kill -9 $pid"
	      kill -9 $pid
	  else
	      kill $pid
	  fi
	  sleep 3;
	done
	echo "Stop ${PROJECT_NAME} successfully."
	rm $PID_FILE
}

function status(){
        if running;then
		echo "${PROJECT_NAME} is running."
        else
                echo "${PROJECT_NAME} is not running."
        fi
        exit 0
}

function help() {
    echo "Usage: BEE {start|stop}" >&2
    echo "       start:             start"
    echo "       stop:              stop"
}

command=$1
shift 1
case $command in
    status)
        status $@;
        ;;
    start)
        start_server $@;
        ;;
    stop)
        stop_server $@;
        ;;
    restart)
        stop_server $@;
        start_server $@;
        ;;
    *)
        help;
        exit 1;
        ;;
esac

