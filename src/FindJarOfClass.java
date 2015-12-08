import java.io.File;   
import java.util.ArrayList;   
import java.util.Enumeration;   
import java.util.List;   
import java.util.zip.ZipEntry;   
import java.util.zip.ZipFile;   
   
public class FindJarOfClass {   
    public String className;   
   
    public List<String> jarFiles = new ArrayList<String>();   
   
    public FindJarOfClass() {   
    }   
   
    public FindJarOfClass(String className) {   
        this.className = className;   
    }   
   
    public void setClassName(String className) {   
        this.className = className;   
    }   
   
    public List<String> findClass(String dir, boolean recurse) {   
        searchDir(dir, recurse);   
        return this.jarFiles;   
    }   
   
    @SuppressWarnings("resource")
	protected void searchDir(String dir, boolean recurse) {   
        try {   
            File d = new File(dir);   
            if (!d.isDirectory()) {   
                return;   
            }   
            for (File files : d.listFiles()){
            	 if (recurse && files.isDirectory()) {   
                     searchDir(files.getAbsolutePath(), true);   
                 } else {   
                     String filename = files.getAbsolutePath();   
                     if (filename.endsWith(".jar")||filename.endsWith(".zip")) {   
                         Enumeration<?> entries = new ZipFile(filename).entries();   
                         while (entries.hasMoreElements()) {   
                             ZipEntry entry = (ZipEntry) entries.nextElement();   
                             String thisClassName = getClassName(entry);   
                             if (thisClassName.equals(this.className) || thisClassName.equals(this.className + ".class")) {   
                                 this.jarFiles.add(filename);   
                             }   
                         }   
                     }   
                 }   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
   
    public List<String> getFilenames() {   
        return this.jarFiles;   
    }   
   
    protected String getClassName(ZipEntry entry) {   
        StringBuffer className = new StringBuffer(entry.getName().replace('/', '.'));   
        return className.toString();   
    }   
   
    public static void main(String args[]) {   
    	   	
    	
		FindJarOfClass findInJar = new FindJarOfClass("com.asuscloud.util.PeriodRollingFileAppander");
		List<String> jarFiles = findInJar.findClass("D:\\workspace\\qualitytest\\QEDlibs\\libs", true);
		if ( jarFiles.size() == 0 )
		{
			System.out.println("Not Found");
		}
		else
		{
			for ( int i = 0; i < jarFiles.size(); i++ )
			{
				System.out.println(jarFiles.get(i));
			}
		}
    }
}   
