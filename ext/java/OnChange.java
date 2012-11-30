package org.substantiality;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class OnChange {

    /**
     * Main program.
     * * @param args Command line arguments - dirs to watch.
     * * @throws IOException in case of I/O problems.
     * * @throws InterruptedException in case the thread was interrupted during watchService.take().
     */
    public static void main(final String... args) throws IOException, InterruptedException {
        final FileSystem fileSystem = FileSystems.getDefault();
        try (final WatchService watchService = fileSystem.newWatchService()) {
            final Map<WatchKey, Path> keyMap = new HashMap<WatchKey, Path>();
            for (final String arg : args.length > 0 ? args : new String[]{"."}) {
                final Path path = Paths.get(arg);
                keyMap.put(path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY), path);
            }
            WatchKey watchKey;
            do {
                watchKey = watchService.take();
                final Path eventDir = keyMap.get(watchKey);
                for (final WatchEvent<?> event : watchKey.pollEvents()) {
                    final WatchEvent.Kind kind = event.kind();
                    final Path eventPath = (Path) event.context();
                    System.out.println(eventDir + ": " + event.kind() + ": " + event.context());
                }
            } while (watchKey.reset());
        }
    }
}
