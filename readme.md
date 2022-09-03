# Console with Monospace font

**Note:** this app is perfect to run your Java code on an Android device where there is no 
console output available. The app redirects the System.out.println to the TextView, 
that is embedded in a ScrollView for longer outputs. 

Source: https://www.baeldung.com/java-compress-and-uncompress

Github: https://github.com/eugenp/tutorials/tree/master/core-java-modules/core-java-io/src/main/java/com/baeldung/zip

Zipping and Unzipping in Java
Last modified: November 24, 2021

by baeldung Java+
Get started with Spring 5 and Spring Boot 2, through the Learn Spring course:

> CHECK OUT THE COURSE

1. Overview

In this quick tutorial, we'll learn how to zip a file into an archive and how to unzip the archive, all using core libraries provided by Java.

These core libraries are part of the java.util.zip package, where we can find all zipping and unzipping related utilities.

2. Zip a File

First, let's have a look at a simple operation, zipping a single file.

For our example, we'll zip a file named test1.txt into an archive named compressed.zip.

Of course, we'll first access the file from a disk:


freestar
public class ZipFile {
public static void main(String[] args) throws IOException {
String sourceFile = "test1.txt";
FileOutputStream fos = new FileOutputStream("compressed.zip");
ZipOutputStream zipOut = new ZipOutputStream(fos);
File fileToZip = new File(sourceFile);
FileInputStream fis = new FileInputStream(fileToZip);
ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
zipOut.putNextEntry(zipEntry);
byte[] bytes = new byte[1024];
int length;
while((length = fis.read(bytes)) >= 0) {
zipOut.write(bytes, 0, length);
}
zipOut.close();
fis.close();
fos.close();
}
}
3. Zip Multiple Files

Next, let's see how to zip multiple files into one zip file. We'll compress test1.txt and test2.txt into multiCompressed.zip:

public class ZipMultipleFiles {
public static void main(String[] args) throws IOException {
List<String> srcFiles = Arrays.asList("test1.txt", "test2.txt");
FileOutputStream fos = new FileOutputStream("multiCompressed.zip");
ZipOutputStream zipOut = new ZipOutputStream(fos);
for (String srcFile : srcFiles) {
File fileToZip = new File(srcFile);
FileInputStream fis = new FileInputStream(fileToZip);
ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
    }
}
4. Zip a Directory

Now let's discuss how to zip an entire directory. We'll compress zipTest into dirCompressed.zip :

public class ZipDirectory {
public static void main(String[] args) throws IOException {
String sourceFile = "zipTest";
FileOutputStream fos = new FileOutputStream("dirCompressed.zip");
ZipOutputStream zipOut = new ZipOutputStream(fos);
File fileToZip = new File(sourceFile);

        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
Note that:

To zip sub-directories, we iterate through them recursively.
Every time we find a directory, we append its name to the descendants ZipEntry name to save the hierarchy.
We also create a directory entry for every empty directory.
5. Unzip an Archive

Now let's unzip an archive and extract its contents.

For this example, we'll unzip compressed.zip into a new folder named unzipTest:


freestar
public class UnzipFile {
public static void main(String[] args) throws IOException {
String fileZip = "src/main/resources/unzipTest/compressed.zip";
File destDir = new File("src/main/resources/unzipTest");
byte[] buffer = new byte[1024];
ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
ZipEntry zipEntry = zis.getNextEntry();
while (zipEntry != null) {
// ...
}
zis.closeEntry();
zis.close();
}
}
Inside the while loop, we'll iterate through each ZipEntry and first check if it's a directory. If it is, then we'll create the directory using the mkdirs() method; otherwise, we'll continue with creating the file:

while (zipEntry != null) {
File newFile = newFile(destDir, zipEntry);
if (zipEntry.isDirectory()) {
if (!newFile.isDirectory() && !newFile.mkdirs()) {
throw new IOException("Failed to create directory " + newFile);
}
} else {
// fix for Windows-created archives
File parent = newFile.getParentFile();
if (!parent.isDirectory() && !parent.mkdirs()) {
throw new IOException("Failed to create directory " + parent);
}

         // write file content
         FileOutputStream fos = new FileOutputStream(newFile);
         int len;
         while ((len = zis.read(buffer)) > 0) {
             fos.write(buffer, 0, len);
         }
         fos.close();
     }
zipEntry = zis.getNextEntry();
}
One note here is that on the else branch, we're also checking if the parent directory of the file exists. This is necessary for archives created on Windows, where the root directories don't have a corresponding entry in the zip file.

Another key point can be seen in the newFile() method:

public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
File destFile = new File(destinationDir, zipEntry.getName());

    String destDirPath = destinationDir.getCanonicalPath();
    String destFilePath = destFile.getCanonicalPath();

    if (!destFilePath.startsWith(destDirPath + File.separator)) {
        throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
    }

    return destFile;
}
This method guards against writing files to the file system outside of the target folder. This vulnerability is called Zip Slip, and we can read more about it here.

6. Conclusion

In this article, we illustrated how to use Java libraries for zipping and unzipping files.

The implementation of these examples can be found over on GitHub.