package com.starLife.in.helper;


public class AttachmentHelper {

     private String filename;
     private String downloadUrl;
     private long fileSize;
     
     

    public String getFilename() {
      return filename;
    }
    public void setFilename(String filename) {
      this.filename = filename;
    }
    public String getDownloadUrl() {
      return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
      this.downloadUrl = downloadUrl;
    }
    public long getFileSize() {
      return fileSize;
    }
    public void setFileSize(long fileSize) {
      this.fileSize = fileSize;
    }
    @Override
    public String toString() {
      return "AttachmentHelper [filename=" + filename + ", downloadUrl=" + downloadUrl + ", fileSize=" + fileSize + "]";
    }
    public AttachmentHelper() {
    }
    public AttachmentHelper(String filename, String downloadUrl, long fileSize) {
      this.filename = filename;
      this.downloadUrl = downloadUrl;
      this.fileSize = fileSize;
    }
 
}
