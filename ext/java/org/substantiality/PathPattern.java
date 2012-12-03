package org.substantiality;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

public class PathPattern {

  Path _root;
  PathMatcher _matcher;

  public Path getRoot() {
    return _root;
  }

  public PathPattern(String path) {
    StringBuilder pattern = new StringBuilder();

    for(Path part: Paths.get(path)) {
      if(pattern.length() != 0 || isWildcard(part)) {
        if(pattern.length() == 0)
          pattern.append("glob:" + part.toString());
        else
          pattern.append("/" + part.toString());
      } else {
        if(this._root == null)
          this._root = part;
        else
          this._root = Paths.get(this._root.toString(), part.toString());
      }
    }

    _matcher = FileSystems.getDefault().getPathMatcher(pattern.toString());
  }

  public boolean match(String path) {
    return match(Paths.get(path));
  }

  public boolean match(Path path) {
    return isChild(_root, path) && (this._matcher == null || this._matcher.matches(path));
  }

  private static boolean isWildcard(Path path) {
    return path.toString().matches(".*[\\*\\?]+.*");
  }

  private static boolean isChild(Path p1, Path p2) {
    Path parent = p2.getParent();
    while ( parent != null ) {
      if ( parent.equals(p1))
        return true;
      parent = parent.getParent();
    }
    return false;
  }
}
