import java.io.FileWriter;
import java.io.IOException;

/**
 * 有些大佬可能会说 为啥不用多线程生成数据 主要是因为就这单线程我cpu块都跑满了（贫穷ORZ）
 * <p>
 * 大家可以无聊的时候跑，或者用服务器跑 后续我会弄个服务接口出来给大家下载测试数据（好吧我错了,服务器小水管还不动！还是大佬们自己创建吧）
 * 最后跑的本地跑的实在是太慢了 丢服务器跑ing！！！！
 * 文件输出出来了 用了10分钟生成43E多的数据 txt占比近20G！
 * 10分钟还是太慢了,最后还是整了个多线程
 */
public class CreateFileData implements Runnable{

    //随机写入40E个数字
    public  static void writeData(int num) {
        StringBuilder sb = new StringBuilder();
        //可以写入个2147483648 数字
        long start = System.currentTimeMillis();
        try {
            FileWriter writer = new FileWriter("test.txt");
            //一次写入
            for (int j = 0; j < num; j++) {
                String random = ((int) (Math.random() * 999999999) + "");
                sb.append(random).append(",");
                if (j % (100 * 10000) == 0) {
                    writer.write(sb.toString());
                    sb = null;
                    sb = new StringBuilder();
                    System.out.println("写入" + j + "行");
                }
            }
            writer.write(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }System.out.println("生成数据成功,耗时：" + (System.currentTimeMillis() - start));
    }



    public static void main(String[] args) {
        int threadNum = 4;
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(new CreateFileData());
            thread.setName("thread-" + i);
            thread.start();
        }
    }

    @Override
    public void run() {
        int i = Integer.MAX_VALUE/2;
        writeData(i);
    }
}
