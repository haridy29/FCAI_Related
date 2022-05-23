import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Vector;

class Block {
    public int[][] block;
    public int code;

    Block() {
        int[][] block = new int[0][0];
        code = -1;
    }

    Block(int[][] comp) {
        block = comp;
        code = -1;
    }

}


public class VectorQ {
    public static class ImageClass {

        public static int[][] readImage(String path) {


            BufferedImage img;
            try {
                img = ImageIO.read(new File(path));

                int hieght = img.getHeight();
                int width = img.getWidth();

                int[][] imagePixels = new int[hieght][width];
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < hieght; y++) {

                        int pixel = img.getRGB(x, y);

                        int red = (pixel & 0x00ff0000) >> 16;
                        int grean = (pixel & 0x0000ff00) >> 8;
                        int blue = pixel & 0x000000ff;
                        int alpha = (pixel & 0xff000000) >> 24;
                        imagePixels[y][x] = red;
                    }
                }

                return imagePixels;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                return null;
            }

        }

        public static void writeImage(int[][] imagePixels, String outPath) {

            BufferedImage image = new BufferedImage(imagePixels.length, imagePixels[0].length, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < imagePixels.length; y++) {
                for (int x = 0; x < imagePixels[y].length; x++) {
                    int value = -1 << 24;
                    value = 0xff000000 | (imagePixels[y][x] << 16) | (imagePixels[y][x] << 8) | (imagePixels[y][x]);
                    image.setRGB(x, y, value);

                }
            }

            File ImageFile = new File(outPath);
            try {
                ImageIO.write(image, "jpg", ImageFile);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }

    static Vector<Block> Blocks = new Vector<Block>();

    static Vector<Block> codeBook = new Vector<Block>();

                                                   //2*2,4*4
    public static void DivideImage(int[][] pixels, int vectorsize) {
        int dv = pixels.length/vectorsize;

        int jumpi = 0,jumpj = 0;

        while (jumpi != dv*vectorsize)
        {
            while (jumpj != dv*vectorsize)
            {
                int[][] v = new int[vectorsize][vectorsize];

                for (int i = 0 + jumpi,x=0; i < vectorsize + jumpi; i++,x++) {
                    for (int j = 0 + jumpj,y=0; j < vectorsize + jumpj; j++,y++) {
                        v[x][y] = pixels[i][j];
                    }
                }

                Block b = new Block(v);
                Blocks.add(b);
                jumpj += vectorsize;
            }
            jumpj = 0;
            jumpi +=vectorsize;
        }

    }

    public static int GetIdxofBlock(Block b, Vector<Vector<Block>> tree) {

        int idx = -1;
        for (int i = 0; i < tree.size(); i++)
            for (int j = 1; j < tree.get(i).size(); j++)
                if (tree.get(i).get(j).block == b.block)
                     idx=i;
        return idx;
    }

    public static int chooseRange(Block bk, Vector<Vector<Block>> tree) {
        int idx = -1,mn =Integer.MAX_VALUE,diff=0;
        for(int k=0;k<tree.size();k++) {

            for (int i = 0; i < bk.block.length; i++) {
                for (int j = 0; j < bk.block.length; j++) {
                    diff += Math.abs(bk.block[i][j]-(tree.get(k).get(0).block[i][j]));
                }
            }
            if(diff<mn)
            {
                mn = diff;
                idx = k;
                diff = 0;
            }
        }
        return idx;
    }

    public static int[][] getAverage(Vector<Block> Blocks) {
        int vectorSize = Blocks.elementAt(0).block.length;
        int[][] mid = new int[vectorSize][vectorSize];
        for (int i = 0; i < vectorSize; i++)
            for (int j = 0; j < vectorSize; j++)
                mid[i][j] = 0;
        for (int k = 0; k < Blocks.size(); k++)
            for (int i = 0; i < vectorSize; i++)
                for (int j = 0; j < vectorSize; j++)
                    mid[i][j] += Blocks.elementAt(k).block[i][j];

        for (int i = 0; i < vectorSize; i++)
            for (int j = 0; j < vectorSize; j++)
                mid[i][j] /= Blocks.size();
        return mid;
    }


    public static void compress(int NumofVectors) throws IOException {

        int value = (int) Math.sqrt(Blocks.size());


        int[][] compBlock = new int[value][value];


        Vector<Vector<Block>> Tree = new Vector<Vector<Block>>();

        int[][] avg = getAverage(Blocks);

        int vectorSize = Blocks.elementAt(0).block.length;

        //split into 2 points
        int[][] avgleft = new int[vectorSize][vectorSize];
        int[][] avgright = new int[vectorSize][vectorSize];


        for (int i = 0; i < vectorSize; i++) {
            for (int j = 0; j < vectorSize; j++) {
                avgleft[i][j] = avg[i][j] - 1;
                avgright[i][j] = avg[i][j] + 1;
            }
        }


        Block b1 = new Block(avgleft), b2 = new Block(avgright);

        Vector<Block> v1 = new Vector<Block>(), v2 = new Vector<Block>();

        v1.add(b1);
        v2.add(b2);

        Tree.add(v1);
        Tree.add(v2);

        int split = (int) (Math.log10(NumofVectors) / Math.log10(2.));

        int flag = 0;

        for (int i = 0; i < split; i++) {

            for (int j = 0; j < Blocks.size(); j++) {
                Block bl = Blocks.elementAt(j);
                int ind = chooseRange(Blocks.elementAt(j), Tree);
                Tree.elementAt(ind).add(bl);
            }

            int treesize = Tree.size();

            Vector<Block> midpoints = new Vector<Block>();

            for (int j = 0; j < treesize; j++) {

                avg = new int[vectorSize][vectorSize];
               avg=getAverage(Tree.get(j));

                Block b = new Block(avg);
                midpoints.add(b);

                //update the avg point in the tree
                Tree.get(j).get(0).block = avg;
            }

            //split into 2 points
            if (i != split - 1) {
                Tree = new Vector<Vector<Block>>();
                for (int r = 0; r < midpoints.size(); r++) {
                    avgleft = new int[vectorSize][vectorSize];
                    avgright = new int[vectorSize][vectorSize];
                    for (int l = 0; l < vectorSize; l++) {
                        for (int m = 0; m < vectorSize; m++) {
                            avgleft[l][m] = midpoints.elementAt(r).block[l][m] - 1;
                            avgright[l][m] = midpoints.elementAt(r).block[l][m] + 1;
                        }
                    }
                    b1 = new Block(avgleft);
                    b2 = new Block(avgright);
                    v1 = new Vector<Block>();
                    v2 = new Vector<Block>();
                    v1.add(b1);
                    v2.add(b2);
                    Tree.add(v1);
                    Tree.add(v2);
                }
            } else if (flag == 0) {
                split += 1;
                flag = 1;
            }


        }

        // save in codeBook
        for (int i = 0; i < Tree.size(); i++) {
            Block CodeBook = new Block();
            CodeBook = Tree.get(i).get(0);
            CodeBook.code = i;
            codeBook.add(CodeBook);
        }

        //compress
        int idx = 0;

        for (int i = 0; i < compBlock.length; i++) {
            for (int j = 0; j < compBlock.length; j++) {
                compBlock[i][j] = GetIdxofBlock(Blocks.elementAt(idx), Tree);
                idx++;
            }

        }
        ObjectOutputStream obj=new ObjectOutputStream(new FileOutputStream("Compress.txt"));
        obj.writeObject(compBlock);
        FileWriter fileWriter=new FileWriter("Comp.txt");
        for (int i = 0; i <compBlock.length ; i++) {
            for (int j = 0; j < compBlock.length; j++) {
                fileWriter.write(compBlock[i][j]+" ");
            }
            fileWriter.write("\n");
        }
            fileWriter.close();
    }

    public static int GetCodeBook(int code) {
        int ind = -1;
        for (int i = 0; i < codeBook.size(); i++)
            if (codeBook.elementAt(i).code == code)
                ind = i;



        return ind;
    }

    public static void Decompress() throws IOException, ClassNotFoundException {
        ObjectInputStream in=new ObjectInputStream(new BufferedInputStream(new BufferedInputStream(new FileInputStream("Compress.txt"))));

        int [][]CompBlock=(int[][])in.readObject();
            System.out.println(CompBlock.length);


        int vectorSize = codeBook.elementAt(0).block.length;

        int ImageLength = (int) Math.sqrt(Blocks.size() * vectorSize * vectorSize);

        int[][] decompBlock = new int[ImageLength][ImageLength];

        int dv = ImageLength / vectorSize;
        int jumpi = 0, jumpj = 0;

        int a = 0, b = 0;

        while (jumpi != dv * vectorSize) {
            while (jumpj != dv * vectorSize) {
                int ind = GetCodeBook(CompBlock[a][b]);
                int[][] cd = codeBook.elementAt(ind).block;
                for (int i = 0 + jumpi, x = 0; i < vectorSize + jumpi; i++, x++) {
                    for (int j = 0 + jumpj, y = 0; j < vectorSize + jumpj; j++, y++) {
                        decompBlock[i][j] = cd[x][y];
                    }
                }
                jumpj += vectorSize;
                b++;
            }
            jumpj = 0;
            b = 0;
            jumpi += vectorSize;
            a++;
        }
        VectorQ.ImageClass.writeImage(decompBlock, "image_out.jpg");


        FileWriter fileWriter=new FileWriter("deComp.txt");
        for (int i = 0; i <decompBlock.length ; i++) {
            for (int j = 0; j < decompBlock.length; j++) {
                fileWriter.write(decompBlock[i][j] + " ");
            }
            fileWriter.write("\n");
        }
        fileWriter.close();
    }


}
