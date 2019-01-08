package com.bf.io;


import java.io.*;

/**
 * @Author bofei
 * @Date 2019/1/8 12:46
 * @Description
 */
public class ToFS implements StorageFile {


    private StorageFile storageFile;
    private File file;


    public ToFS(StorageFile storageFile) {
        this.storageFile = storageFile;
    }

    @Override
    public boolean open(String id, String flag) {
        String firstpath = "path";
        String hash = Util.hash(id);
        file = new File(firstpath + File.separator + hash + File.separator + id);
        return true;
    }

    @Override
    public boolean read(byte[] data, int off, int size, Integer length) {

        try (FileInputStream fio = new FileInputStream(file)) {
            data = new byte[1024000000];
            int k = 0;
            while ((k = fio.read(data)) != -1) {
                System.out.println(new String(data));
                length = k;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean write(byte[] data, int off, int size, Integer length) {
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean close() {
        return true;
    }
}
