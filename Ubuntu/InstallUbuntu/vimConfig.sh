echo -e "\n############## vim config ###########\n";
sudo rm ~/.vimrc ;
touch ~/.vimrc ;
echo "set nu">>~/.vimrc;
echo "set ts=4">>~/.vimrc;
echo "set expandtab">>~/.vimrc;

# vim显示行号
# :set nu
# 不显示行号
# :set nonu
# 
# ===========================================
# ####    vim中将tab键转化为空格
# linux下的vi一般是vim的链接，我们在用户主目录下创建.vimrc文件：
# set ts=4  (注：ts是tabstop的缩写，设TAB宽4个空格) 
# set expandtab
# 
# 对于已保存的文件，可以使用下面的方法进行空格和TAB的替换：
# 
# TAB替换为空格：  
# :set ts=4
# :set expandtab
# :%retab!
#     
# 空格替换为TAB：
#     :set ts=4
#     :set noexpandtab
#     :%retab!
