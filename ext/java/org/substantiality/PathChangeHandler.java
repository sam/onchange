package org.substantiality;

import java.nio.file.Path;

public interface PathChangeHandler {
    public void call(Path path);
}
