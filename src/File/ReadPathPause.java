/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Humphrey
 */
public class ReadPathPause {
public String readPathPause() throws IOException{
    Stream<String> lines = Files.lines(Paths.get("src/jes/path/pause.txt"));
    List<String> content = lines.collect(Collectors.toList());
    String strcontent=content.toString();
    //strcontent = strcontent.replace("\\", "\\\\");
    strcontent = strcontent.replace("[", "");
    strcontent = strcontent.replace("]", "");
    return strcontent;
   }        
}
