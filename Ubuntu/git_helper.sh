function s.gitlost(){
    git fsck --lost-found;
#找回删除的commit,之后可以用merge加回来
}

function s.gitarchive(){
    git archive -o $3 $1 $2;
#    git archive <COMMIT_ID> <FILE_PATH> 把commit中的文件存档　-o表未output，应是一个zip的文件名
#    FILE_PATH可以用  src/*　型式，也可以用　$(git diff --name-only COMMIT_ID)　型式
}

function s.gitsilence(){
    git update-index --assume-unchanged  $1;
#不跟踪指定文件的变化
    #取消用git update-index --no-assume-unchanged
}
