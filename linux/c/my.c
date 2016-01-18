#include <stdio.h>
#include <unistd.h>
int main(int p,char **argv){
    printf("----------------------\n");
    /*char *a = "abc";
    printf("%s",a);
    */
    char ch='a';
    while((ch = getopt(p,argv,"a:b")) !=-1){
        printf("%c,%s\n",ch,optarg);
    }

    printf("\n----------------------\n");
}
