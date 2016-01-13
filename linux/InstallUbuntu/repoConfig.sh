echo -e "\n############# repo config #############\n"
source common.sh;
sudo apt-get install curl
sudo curl https://storage.googleapis.com/git-repo-downloads/repo > repo
sudo chmod 777 repo
sudo mv repo $SAMARIUM_DIR/$dir_bin

