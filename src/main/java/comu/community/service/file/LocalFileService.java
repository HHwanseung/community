package comu.community.service.file;

import comu.community.exception.FileUploadFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
@PropertySource("classpath:application.yml")
public class LocalFileService implements FileService{

    @Value("${upload.image.location}")
    private String location;

    @PostConstruct
    void postConstruct() {
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    @Override
    public void upload(MultipartFile file, String filename) {
        try {
            file.transferTo(new File(location + filename));
        }catch (IOException e) {
            throw new FileUploadFailureException(e);
        }
    }

    @Override
    public void delete(String filename) {
        new File(location + filename).delete();
    }
}
