#sudo 默认使用/etc/sudoers中的secure_path中的path，以下使当前path作为sudo的path 
alias s.sudoCurrentPath='sudo env PATH=$PATH';

function s.mountMiRouter(){
    dir=tempSambaDir;
    if [ x$3 = x ]; then 
        host=192.168.31.1;
    else
        host=$3;
    fi
    if [ ! -d $dir ]; then
        mkdir $dir;
    fi
    echo $host;
    sudo mount -rw -o username=$1,password=$2 //$host/share $dir
}

function s.umountMiRouter(){
    dir=tempSambaDir;
    if [ -d $dir ]; then
        sudo umount $dir;
        rmdir $dir;
    fi
}
#vi多行注释 :<开始行号,结束行号或$>s/^/#/  e.g. 19,$s/^/#/

#-a file exists. 
#-b file exists and is a block special file. 
#-c file exists and is a character special file. 
#-d file exists and is a directory. 
#-e file exists (just the same as -a). 
#-f file exists and is a regular file. 
#-g file exists and has its setgid(2) bit set. 
#-G file exists and has the same group ID as this process. 
#-k file exists and has its sticky bit set. 
#-L file exists and is a symbolic link. 
#-n string length is not zero. 
#-o Named option is set on. 
#-O file exists and is owned by the user ID of this process. 
#-p file exists and is a first in, first out (FIFO) special file or 
#named pipe. 
#-r file exists and is readable by the current process. 
#-s file exists and has a size greater than zero. 
#-S file exists and is a socket. 
#-t file descriptor number fildes is open and associated with a 
#terminal device. 
#-u file exists and has its setuid(2) bit set. 
#-w file exists and is writable by the current process. 
#-x file exists and is executable by the current process. 
#-z string length is zero. 

function s.free(){
#proc文件系统是一个伪文件系统，它只存在内存当中，而不占用外存空间。它以文件系统的方式为访问系统内核数据的操作提供接口
    cat /proc/meminfo |grep MemFree;
}

function s.here(){
#文件夹图形界面
    nautilus . ;
}

function s.pic(){
#全屏图片
    eog -f $1 ;
}

function s.back(){
    ($1 &) ;    
#shell 前台／后台命令
#1. <commend>+& :让命令在后台执行。如 eclipse &,让eclipse在后台启动，不再占用shell.但是输出还会出现在shell里。（eclipse &）连输出也不占shell
#2.ctrl+z 命令:让前台执行的任务暂停，空出shell. 
#(1) CTRL+Z停止进程并放入后台     (2) jobs显示当前暂停的进程     (3) bg %N使第N个任务在后台运行(%前有空格)     (4) fg %N使第N个任务在前台运行     默认bg,fg不带%N时表示对最后一个进程操作
#
#nohup与&
#nohup ping XXX :后台执行，shell可见，ctrl+c可中止，退出shell仍在执行.
#nohup ping XXX ＆:后台执行,shell不可见。
#& ping XXX :后台执行，shell不可见，退出shell停止执行
}

function s.getUbuntuSource(){
    temp=`type $1`;
    tmp=/${temp#*/} #与％分别是左右截取字符串
    echo $tmp;
    dkpgpaht=`sudo dpkg -S $tmp`;
    sourcepath=${dkpgpaht%%:*}; #%最小匹配　%%最大匹配
    echo $sourcepath;
    (apt-get source $sourcepath);
}


# dpkg -l
# 列出所有已安装的软件包。

