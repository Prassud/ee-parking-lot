BIN_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
echo $0
if [ -z "$1" ]
  then
     java -jar parking-lot.jar "process_from_CONSOLE"
     exit 1
fi
file="$BIN_DIR/$1"
java -jar parking-lot.jar "process_from_file" $file
