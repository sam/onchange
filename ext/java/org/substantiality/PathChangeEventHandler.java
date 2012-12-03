package org.substantiality;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;

public interface PathChangeEventHandler {
    public void call(Path path, StandardWatchEventKinds event);
}
