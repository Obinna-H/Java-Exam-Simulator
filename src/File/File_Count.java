/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Humphrey
 */
public class File_Count {
ReadPathFile read = new ReadPathFile();
public int CountFiles() throws IOException{
int count= new File(read.readPathFile()).list().length;  
return count;
}    
}
