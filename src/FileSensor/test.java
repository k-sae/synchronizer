package FileSensor;

import com.sun.beans.util.Cache;
import com.sun.xml.internal.ws.api.model.ParameterBinding;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class test {

    public static void main(String [] args){


    
    Path folder = Paths.get("F:\\a\\");


        WatchService watchService = null;
        try {
            watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            folder.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
            WatchKey watchKey;

            do {
                watchKey= watchService.take();

                System.out.println("hhhh");
                System.out.println(new Changeanalyzer().analyze(watchKey));
//                for( WatchEvent <?>event:watchKey.pollEvents()){
//
//
//                WatchEvent.Kind<?> kind= event.kind() ;
//                    Path f= (Path) event.context();
//                System.out.println(kind+"  :  "+f);}

            }while (watchKey.reset());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            watchService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}