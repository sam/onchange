package org.substantiality;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;

public interface PathChangeHandler {
    public void call(Path path, StandardWatchEventKinds event);
}
