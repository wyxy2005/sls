function s.adbdump(){
    adb shell mkdir /data/data/sls;
    myFileName=$1.$(date +%H-%M-%S);
    adb shell am dumpheap $1 /data/data/sls/$myFileName.hprof
    echo "/data/data/sls/$myFileName.hprof"
}
