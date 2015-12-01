import urllib2
from bs4 import BeautifulSoup
import slsfunctions
import os

host = ""
image_dir = os.path.join(os.environ['HOME'],'image');

def go(url):
    protocol, address=urllib2.splittype(url)
#    print protocol,address
    if protocol == "http":
        global host;
        host,path=urllib2.splithost(address)
#        print host,path;
        content = getPageContent(url);
        soup = BeautifulSoup(content,'html.parser');
        getAllImage(soup);
        getAllHyperlink(soup);
    else :
        print 'URL is not http'
        
def getPageContent(url):
    request = urllib2.Request(url)
    '''
     Accept-Encoding:gzip
    '''    
    req_header = {'User-Agent':'Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11',
                  'Accept':'text/html;q=0.9,*/*;q=0.8',
                  'Accept-Charset':'utf-8,gb2312;q=0.7,*;q=0.7',
                  'Connection':'close',
                  'Referer':None
                  }

    request = urllib2.Request(url,None,req_header)
    page = urllib2.urlopen(request,None,60);
    content = page.read();
    page.close();
    return content;


def getAllImage(soup):
    images = soup.findAll('img');
    for img in images:
        src = img["src"]
        if src.find("http") == 0:
            image_url = src;
        elif src.find("//") == 0:
            image_url = 'http:'+str(src);
        elif src.find("/") == 0 and len(src) > 1:
            return 
        else :
            return
        
        w , h=slsfunctions.getImageSizeFromURL(image_url);
        if  h > 300 and w > 300:
            slsfunctions.downloadImage(image_url, image_dir);    

def getAllHyperlink(soup):
    hryperlinks = soup.findAll("a");
    for h in hryperlinks :
        if h.has_attr("href"):
            href = h['href'];
            print href;
        else :
            print "******************",h;
    

#--------------main-------------------------
if __name__ == "__main__" :
    go("http://www.newsmth.net/nForum/board/Announce");

