package org.substantiality;

import java.io.IOException;
import java.nio.file.*;

public class OnChange {

    PathPattern[] _paths;

    public OnChange(final String... paths) {
//        this._paths = paths;
    }

    public PathPattern[] getPaths() {
        return _paths;
    }

    public OnChange created(final PathChangeHandler handler) throws IOException, InterruptedException {
        bind(new PathChangeEventHandler() {
            @Override
            public void call(Path path, StandardWatchEventKinds event) {
                if (event.equals(StandardWatchEventKinds.ENTRY_CREATE))
                    handler.call(path);
            }
        });
        return this;
    }

    public OnChange deleted(final PathChangeHandler handler) throws IOException, InterruptedException {
        bind(new PathChangeEventHandler() {
            @Override
            public void call(Path path, StandardWatchEventKinds event) {
                if (event.equals(StandardWatchEventKinds.ENTRY_DELETE))
                    handler.call(path);
            }
        });
        return this;
    }

    public OnChange modified(final PathChangeHandler handler) throws IOException, InterruptedException {
        bind(new PathChangeEventHandler() {
            @Override
            public void call(Path path, StandardWatchEventKinds event) {
                if (event.equals(StandardWatchEventKinds.ENTRY_MODIFY))
                    handler.call(path);
            }
        });
        return this;
    }

    public OnChange bind(final PathChangeEventHandler handler) throws IOException, InterruptedException {
//        final FileSystem fileSystem = FileSystems.getDefault();
//        try (final WatchService watchService = fileSystem.newWatchService()) {
//            final Map<WatchKey, Path> keyMap = new HashMap<WatchKey, Path>();
//            for (final PathPattern arg : _paths.length > 0 ? _paths: new PathPattern[]{"test/**/*.rb"}) {
//                final Path path = Paths.get(arg);
//                keyMap.put(path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY), path);
//            }
//            WatchKey watchKey;
//            do {
//                watchKey = watchService.take();
//                final Path eventDir = keyMap.get(watchKey);
//                for (final WatchEvent<?> event : watchKey.pollEvents()) {
//                    final WatchEvent.Kind kind = event.kind();
//                    final Path eventPath = (Path) event.context();
//                    System.out.println(eventDir + ": " + event.kind() + ": " + event.context());
//                }
//            } while (watchKey.reset());
//        }
        return this;
    }
}
