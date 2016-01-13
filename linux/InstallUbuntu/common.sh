export SAMARIUM_DIR=/samarium_root

dir_data=data;
dir_software=software;
dir_bin=bin;

#check or init
if [ ! -d $SAMARIUM_DIR ]; then
    sudo mkdir $SAMARIUM_DIR;
fi
if [ ! -d $SAMARIUM_DIR/$dir_data ]; then
    sudo mkdir $SAMARIUM_DIR/$dir_data;
fi
if [ ! -d $SAMARIUM_DIR/$dir_bin ]; then
    sudo mkdir $SAMARIUM_DIR/$dir_bin;
fi
if [ ! -d $SAMARIUM_DIR/$dir_software ]; then
    sudo mkdir $SAMARIUM_DIR/$dir_software;
fi
