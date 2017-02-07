package FileSensor;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created by PC - MeiR on 2/7/2017.
 */
public class Folderlistener implements Runnable {
    private Path FOLDER_PATH;
    private WatchService watchService;
    private WatchKey watchKey;



    public Folderlistener(String path) throws IOException {
        this.FOLDER_PATH = Paths.get(path);
        this.watchService = FileSystems.getDefault().newWatchService();
        this.FOLDER_PATH.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
    }

    /**
     * @// TODO 1: 2/7/2017 add log to capture changes
     * @// TODO 2: 2/7/2017 analyze changes
     */
    @Override
    public void run() {

        do {
            try {
                watchKey= watchService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for( WatchEvent <?>event:watchKey.pollEvents()){

                WatchEvent.Kind<?> kind= event.kind() ;
                Path f= (Path) event.context();
                System.out.println(kind+"  :  "+f);}
        }while (watchKey.reset());


    }
}
