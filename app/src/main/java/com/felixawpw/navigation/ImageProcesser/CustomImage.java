package com.felixawpw.navigation.ImageProcesser;

import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomImage {

    public static final String TAG = "CustomImage::class";
    //<editor-fold defaultstate="collapsed" desc="ENCAPSULATED FIELDS">

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ImageView getOriginalImage() {
        return getImageView();
    }

    public void setOriginalImage(ImageView originalImage) {
        this.setImageView(originalImage);
    }

    public int[][] getImageArray() {
        return imageArray;
    }

    public void setImageArray(int[][] imageArray) {
        this.imageArray = imageArray;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getWidthDPI() {
        return widthDPI;
    }

    public void setWidthDPI(int widthDPI) {
        this.widthDPI = widthDPI;
    }

    public int getHeightDPI() {
        return heightDPI;
    }

    public void setHeightDPI(int heightDPI) {
        this.heightDPI = heightDPI;
    }

    public int getBitsPerPixel() {
        return bitsPerPixel;
    }

    public void setBitsPerPixel(int bitsPerPixel) {
        this.bitsPerPixel = bitsPerPixel;
    }

    public float getWidthInch() {
        return widthInch;
    }

    public void setWidthInch(float widthInch) {
        this.widthInch = widthInch;
    }

    public float getHeightInch() {
        return heightInch;
    }

    public void setHeightInch(float heightInch) {
        this.heightInch = heightInch;
    }
    //</editor-fold>

    private ImageView imageView;
    private int[][] imageArray;

    private int width;
    private int height;
    private int widthDPI;
    private int heightDPI;
    private int bitsPerPixel;
    private float widthInch;
    private float heightInch;

    public CustomImage(ImageView originalImage, int[][] imageArray) {
        this.setImageArray(imageArray);
        setWidth(imageArray.length);
        setHeight(imageArray[0].length);
    }

    public CustomImage(ImageView originalImage, JSONObject json) throws JSONException{
        String[] imageArrayToken = json.getString("image_array").split("\r\n");

        imageArray = new int[imageArrayToken[0].length()][imageArrayToken.length];
        for (int i = 0; i < imageArrayToken.length; i++) {
            for (int j = 0; j < imageArrayToken[i].length(); j++) {
                imageArray[j][i] = Character.getNumericValue(imageArrayToken[i].charAt(j));
            }
        }

        String[] imageDataToken = json.getString("image_data").split("\r\n");

        for (String token : imageDataToken) {
            String[] parsed = token.split(": ");
            switch (parsed[0]) {
                case "Width":
                    width = Integer.parseInt(parsed[1]);
                    break;
                case "Height":
                    height = Integer.parseInt(parsed[1]);
                    break;
                case "Bits Per Pixel":
                    bitsPerPixel = Integer.parseInt(parsed[1]);
                    break;
                case "Physical Height Dpi":
                    heightDPI = Integer.parseInt(parsed[1]);
                    break;
                case "Physical Height Inch":
                    heightInch = Float.parseFloat(parsed[1]);
                    break;
                case "Physical Width Dpi":
                    widthDPI = Integer.parseInt(parsed[1]);
                    break;
                case "Physical Width Inch":
                    widthInch = Float.parseFloat(parsed[1]);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder classString = new StringBuilder();

        classString.append(String.format("%s: %s\n", "Width", width));
        classString.append(String.format("%s: %s\n", "Height", height));
        classString.append(String.format("%s: %s\n", "Bits per Pixel", bitsPerPixel));
        classString.append(String.format("%s: %s\n", "Physical Height Dpi", heightDPI));
        classString.append(String.format("%s: %s\n", "Physical Width Dpi", widthDPI));
        classString.append(String.format("%s: %s\n", "Physical Height Inch", heightInch));
        classString.append(String.format("%s: %s\n", "Physical Width Inch", widthInch));

        return classString.toString();
    }
}
