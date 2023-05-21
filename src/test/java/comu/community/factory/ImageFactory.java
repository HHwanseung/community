package comu.community.factory;

import comu.community.entity.board.Image;

public class ImageFactory {

    public static Image createImage() {
        return new Image("origin_filename.jpg");
    }
}
