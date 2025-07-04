import static java.lang.Integer.MAX_VALUE;

void main() throws IOException {
    var root = readln("Enter the root directory: ");
    
    var paths = pathsToEventsAndGamesIn(root);
    
    paths.forEach(path -> println(path.toString()));
}

private static List<Path> pathsToEventsAndGamesIn(String root) throws IOException {
    try (var paths = Files.walk(Path.of(root), MAX_VALUE, FileVisitOption.FOLLOW_LINKS)) {
        return paths
                .skip(1)
                .filter(path -> !path.toString().contains(".git"))
                .toList();
    }
}
