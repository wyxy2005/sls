echo "-----load my shell scripts in $SAMARIUM_SCRIPT_PATH -----";
for i in `find $SAMARIUM_SCRIPT_PATH -name "sls_*_helper.sh"`
do 
    source $i;
done
