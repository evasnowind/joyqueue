package io.chubao.joyqueue.broker.archive.store.hbase;

/**
 * Created by chengzhiliang on 2018/12/13.
 */
//FIXME: 单元测试未通过

public class HbaseSerializerTest {


//    @Test
//    public void resetPosition() {
//        ByteBuffer allocate = ByteBuffer.allocate(1024);
//        byte[] val = "test string.".getBytes(Charset.forName("utf-8"));
//        allocate.putInt(val.length);
//        allocate.put(val);
//        allocate.flip();
//
//        int len = 4 + val.length;
//
//        int anInt = allocate.getInt();
//        byte[] bytes = new byte[anInt];
//        allocate.get(bytes);
//        String getVal = new String(bytes, Charset.forName("utf-8"));
//        System.out.println(getVal);
//
//        allocate.position(allocate.position() - len);
//
//
//        byte[] bytes2 = new byte[allocate.getInt()];
//        allocate.get(bytes2);
//        String getVal2 = new String(bytes, Charset.forName("utf-8"));
//        System.out.println(getVal2);
//
//
//    }
}