package com.chencd97.uc2mp3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * 网易云音乐缓存解码器(uc2mp3)
 *
 * @version 1.0.1
 * @author chencd97
 * @url https://github.com/chencd97/uc2mp3
 *
 */
public class uc2mp3 {

	private static DataInputStream dis = null;
	private static DataOutputStream dos = null;
	private static File inFile = null;
	private static File outFile = null;

	public static void main(String[] args) throws InterruptedException {

		Scanner scan = new Scanner(System.in);
		System.out.println("网易云音乐缓存解码器(uc2mp3)v1.0.1 By_chencd97");
		System.out.println("欢迎使用,本工具可以将网易云音乐的缓存文件(.uc)格式解码成(.mp3)格式");
		System.out.println("免责声明: 本工具仅供代码的测试，学习和交流，严禁用于商业用途，如产生法律纠纷与本人无关");
		System.out.println("项目地址: https://github.com/chencd97/uc2mp3 \n");
		System.out.println("请输入要解码的文件路径【文件路径的开头和结尾不能包含引号】");
		System.out.println("[Win10用户可直接将要解码的文件拖放至此窗口]");
		int time = 1;
		do {
			System.out.print("Path" + (time++) + ":");
			String path = scan.nextLine();
			if ("exit".equals(path)) {
				System.out.println("感谢使用！");
				Thread.sleep(1000);
				break;
			}
			changeFile(path);
		} while (true);
		scan.close();

	}

	private static void changeFile(String filePath) {

		inFile = new File(filePath);
		if (!inFile.exists()) {
			System.out.println("错误，您所访问的文件不存在或您无权访问该文件！");
			System.out.println("请检查文件路径拼写是否有误 (文件路径的开头和结尾不能包含引号)");
			return;
		}
		String fileName = inFile.getName();
		boolean end = fileName.endsWith(".uc");
		if (!end) {
			System.out.println("错误，文件名必须以[.uc]结尾！");
			return;
		}
		System.out.println("您要转换的文件是：" + fileName);// -----------------
		int index = fileName.indexOf(".");
		fileName = fileName.substring(0, index);
		index = filePath.lastIndexOf("\\") + 1;
		filePath = filePath.substring(0, index);
		outFile = new File(filePath + fileName + ".mp3");
		try {
			dis = new DataInputStream(new FileInputStream(inFile));
			dos = new DataOutputStream(new FileOutputStream(outFile));
			byte[] data = new byte[1024 * 4];
			int len = -1;
			while ((len = dis.read(data)) != -1) {
				for (int i = 0; i < len; i++) {
					data[i] ^= 0xa3;
				}
				dos.write(data, 0, len);
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("非法操作");
			e.printStackTrace();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
					System.out.println("非法操作");
				}
			}
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e) {
					System.out.println("非法操作");
				}
			}
		}
		System.out.println("转换已完成！输出文件夹：" + filePath + fileName + ".mp3 \n");
	}
}
