package ie.corballis.fixtures.io.write;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import ie.corballis.fixtures.util.ResourceUtils;

import static ie.corballis.fixtures.settings.SettingsHolder.settings;

public class DefaultSnapshotWriter extends AbstractFixtureWriter implements SnapshotFixtureWriter {

    public static final String AUTO_GENERATED_FOR = "_AUTO_GENERATED_FOR_";

    public DefaultSnapshotWriter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    public File write(Class testClass, String fixtureName, Object contents) throws IOException {
        try {
            String fixtureFileName = testClass.getSimpleName();
            String fixtureFileFolder = getFixtureFileFolder(testClass);
            return write(fixtureFileFolder,
                    fixtureFileName,
                    fixtureName,
                    contents, settings().getSnapshotFileNamingStrategy(),
                    (contentMap) -> appendAutoGeneratedFor(testClass, contentMap));
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    protected Object appendAutoGeneratedFor(Class testClass, Map<String, Object> contentMap) {
        return contentMap.put(AUTO_GENERATED_FOR, testClass.getName());
    }

    protected String getFixtureFileFolder(Class testClass) {
        return ResourceUtils.getResourceFilePath(testClass);
    }
}
