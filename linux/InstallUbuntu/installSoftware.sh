echo -e "\033[41;33m install rar \033[0m"
#解压缩用 rar x <file> .
sudo apt-get install rar;

echo -e "\033[41;33m install vim \033[0m"
sudo apt-get install vim;

#文件对比
echo -e "\033[41;33m install meld \033[0m"
sudo apt-get install meld;

#player
echo -e "\033[41;33m install smplayer\033[0m"
sudo apt-get install smplayer; 
sudo apt-get remove vlc;

echo -e "\033[41;33m install curl\033[0m"
sudo apt-get install curl;
echo -e "\033[41;33m GoAgent \033[0m"
#dir=/opt/goagent
#if [ ! -d $dir ]; then
#    sudo mkdir $dir;
#    cd /opt;
#    sudo git clone https://github.com/goagent/goagent.git
#fi


#browser
echo -e "\033[41;33m install chrome\033[0m"
count=`dpkg -l|grep google-chrome|wc -l`
if [ $count -ge 1 ];then 
    echo "google-chrome is installed"
else
(wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && sudo dpkg -i google-chrome-stable_current_amd64.deb  && rm google-chrome-stable_current_amd64.deb );
fi
