package FileSensor;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;

/**
 * Created by PC - MeiR on 2/7/2017.
 */
public class Changeanalyzer {

     private boolean
             DELETE,
             CREATE,
             MODIFY;

    public String  analyze(WatchKey watchKey){

        for( WatchEvent <?>event:watchKey.pollEvents()){

            WatchEvent.Kind<?> kind= event.kind() ;
           // Path f= (Path) event.context();
            if((kind.toString()).equals("ENTRY_MODIFY"))
                this.MODIFY=true;
            else if((kind.toString()).equals("ENTRY_DELETE"))
                this.DELETE=true;
            else if((kind.toString()).equals("ENTRY_CREATE"))
                this.CREATE=true;

        }

        return this.detect();
    }
    private String detect(){
        if(this.MODIFY&&!this.CREATE&&!this.DELETE)
            return "ENTRY_MODIFY";
        else if(this.MODIFY||this.CREATE&&!this.DELETE)
            return "ENTRY_CREATE";
        else if(!this.MODIFY&&!this.CREATE&&this.DELETE)
            return "ENTRY_DELETE";

        return null;
    }

}
