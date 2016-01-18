function sls.resign() {
rm -rf ~/sign/tmp 
rm signed.apk
mkdir ~/sign/tmp
mkdir ~/sign/tmp/temp
cp $1 ~/sign/tmp/tmp.apk
unzip ~/sign/tmp/tmp.apk -d ~/sign/tmp/temp
rm -rf ~/sign/tmp/temp/META-INF
(cd ~/sign/tmp/temp
zip -qr ~/sign/tmp/temp.apk *)
/opt/jdk1.6.0_35/bin/jarsigner -verbose -keystore ~/sign/debug.keystore -signedjar ~/sign/signed.apk ~/sign/tmp/temp.apk androiddebugkey android -keypass android
}
