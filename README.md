# Image corrector (Lab 03)
Symple application provide opportunities to modify images
### Supported transformations
* Linear Contrasting
* Histogram Equalization
* Smoothing Filter


Test folder results:
![](https://github.com/ViktorHi/kg-lab03/blob/master/doc/img/histogram.png)
![](https://github.com/ViktorHi/kg-lab03/blob/master/doc/img/linear.png)
![](https://github.com/ViktorHi/kg-lab03/blob/master/doc/img/smoothing.png)
Smooth filter
Used a gaussian filter:
1  | 3  | 1
--
3  | 9  | 3
1  | 3  | 1


![before transformation](https://github.com/ViktorHi/kg-lab03/blob/master/doc/img/examples/3/source.png "before")
![after transformation](https://github.com/ViktorHi/kg-lab03/blob/master/doc/img/examples/3/smooth.png "after")


Folder with examples [link](https://github.com/ViktorHi/kg-lab03/tree/master/doc/img/examples)
# Build 
To build application from sources you should use *Maven* 

1. go to root folder of **image-corrector** project and run: 
    * mvn compile
    * mvn javafx:run

# Artifacts
### image-corrector.rar [link](https://github.com/ViktorHi/kg-lab03/tree/master/artifact/crossplatform)
Artifact for windows user
To run it you should unzip image-corrector.rar and open image-corrector.exe

### image-corrector.jar [link](https://github.com/ViktorHi/kg-lab03/tree/master/artifact/win)
Crosplatform artifact to run this java 15 should be installed
* You can use double click, if all java configurations installed successfully
* Or *java -jar graphic-reader.jar* from command line or terminal 

This jar should work on Linux, Mac and Windows.


# Documentation 
You can find project documentation here [link](https://github.com/ViktorHi/kg-lab03/tree/master/doc)

