import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import static java.lang.Integer.MAX_VALUE;

void main() throws Exception {
    var root = readln("Enter the root directory: ").trim();

    var paths = pathsToEventsAndGamesIn(root);

    var events = eventsFrom(paths);
    println(events);

    Drive drive = buildDrive();

    var aFileInTheDrive = getAFileIn(drive);

    println(aFileInTheDrive);
}

private static com.google.api.services.drive.model.File getAFileIn(Drive drive)
        throws IOException {
    var files =
            drive
                    .files()
                    .list()
                    .execute()
                    .getFiles();
    
    if (files.isEmpty())
        return null;
    
    return files.getFirst();
}

private static List<Path> pathsToEventsAndGamesIn(String root) throws IOException {
    try (var paths =
                 Files.walk(Path.of(root), MAX_VALUE, FileVisitOption.FOLLOW_LINKS)
    ) {
        return paths
                .skip(1)
                .filter(path -> !path.toString().contains(".git"))
                .toList();
    }
}

private List<String> eventsFrom(List<Path> paths) {
    return paths
            .stream()
            .map(Path::toFile)
            .filter(File::isDirectory)
            .map(file -> eventName(file))
            .toList();
}

private String eventName(File file) {
    var parts = file.getAbsolutePath().split("/");

    return parts[parts.length - 1];
}

private Drive buildDrive() throws Exception {
    String fileName = System.getenv("CREDENTIALS_FILE");

    var credentials = credentialsFrom(fileName);

    var builder =
            new Drive.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    new HttpCredentialsAdapter(credentials)
            );

    return builder.setApplicationName("PGN Uploader").build();
}

private GoogleCredentials credentialsFrom(String fileName) throws Exception {
    var credentialFileStream = getClass().getResourceAsStream(fileName);

    return GoogleCredentials
            .fromStream(credentialFileStream)
            .createScoped(DriveScopes.DRIVE_METADATA_READONLY);
}
