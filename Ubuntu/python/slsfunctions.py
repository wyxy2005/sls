import cStringIO, urllib2, Image
import os
def getImageSizeFromURL(url):
    file = urllib2.urlopen(url,timeout=60)
    tmpIm = cStringIO.StringIO(file.read())
    img = Image.open(tmpIm)
    tmpIm.close()
    imgSize = img.size
    return imgSize;

def downloadImage(image_url,image_dir):
    if  not os.path.exists(image_dir):
        os.makedirs(image_dir);
    filename = os.path.basename(image_url);
    of=open(os.path.join(image_dir,filename), 'w+b');
    q=urllib2.urlopen(image_url,timeout=60)
    of.write(q.read())
    q.close()
    of.close()


#--------------main-------------------------
if __name__ == "__main__" :
    print getImageSizeFromURL('http://www.baidu.com/img/bd_logo1.png')
    downloadImage('http://www.baidu.com/img/bd_logo1.png', os.path.join(os.environ['HOME'],'image'));

