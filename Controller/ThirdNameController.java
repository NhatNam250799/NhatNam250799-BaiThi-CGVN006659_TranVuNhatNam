package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import Model.ThirdName;

public class ThirdNameController {

	private static String filePathString = "src/data/contacts.csv";

	public static void writeFileThirdName(List<ThirdName> thirdNameList) throws IOException {
		File file = new File(filePathString);
		FileWriter fw = new FileWriter(file, false);
		BufferedWriter bw = new BufferedWriter(fw);
		boolean first = true;
		for (ThirdName thirdName : thirdNameList) {
			if (!first) {
				bw.newLine();
			}
			bw.write(thirdName.getSoDienThoai() + "," + thirdName.getNhom() + "," + thirdName.getHoVaTen() + ","
					+ thirdName.getGioiTinh() + "," + thirdName.getDiaChi() + "," + thirdName.getNgaySinh() + ","
					+ thirdName.getEmail());
			first = false;
		}
		bw.close();
	}

	public static List<ThirdName> readFiletThirdNames() {
		File file = new File(filePathString);
		FileReader fr = null;
		BufferedReader br = null;
		List<ThirdName> thirdNameList = new ArrayList<ThirdName>();
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line = null;
			do {
				line = br.readLine();
				if (line != null) {
					String soDienthoai = line.split(",")[0];
					String nhom = line.split(",")[1];
					String hoVaTen = line.split(",")[2];
					String gioiTinh = line.split(",")[3];
					String diaChi = line.split(",")[4];
					int ngaySinh = Integer.parseInt(line.split(",")[5]);
					String eMail = line.split(",")[6];
					thirdNameList.add(new ThirdName(soDienthoai, nhom, hoVaTen, gioiTinh, diaChi, ngaySinh, eMail));
				}
			} while (line != null);
			br.close();
		} catch (EOFException e) {
			thirdNameList = new ArrayList<ThirdName>();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return thirdNameList;
	}

	private List<ThirdName> readFromFile() {
		try (ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(filePathString))) {
			return (List<ThirdName>) oiStream.readObject();

		} catch (IOException | ClassNotFoundException e) {
			return null;
		}
	}

	public void addThirdName() {
		Scanner scanner = new Scanner(System.in);
		List<ThirdName> thirdNames = new ArrayList<ThirdName>();
		thirdNames = readFiletThirdNames();

		try {
			System.out.print("Nhập số điện thoại: ");
			String soDienthoai = scanner.nextLine();
			if (!soDienthoai.matches("\\d{10}")) {
				System.out.println("Số điện thoại không hợp lệ!");
				return;
			}

			System.out.print("Nhập nhóm: ");
			String nhom = scanner.nextLine();

			System.out.print("Nhập họ tên: ");
			String hoVaTen = scanner.nextLine();

			System.out.print("Nhập giới tính: ");
			String gioiTinh = scanner.nextLine();
			while (true) {

				if (gioiTinh.equalsIgnoreCase("Nam") || gioiTinh.equalsIgnoreCase("Nữ")) {

				} else {
					System.out.print("Giới tính không hợp lệ! Vui lòng nhập lại (Nam/Nữ): ");
				}
				break;
			}

			System.out.print("Nhập địa chỉ: ");
			String diaChi = scanner.nextLine();

			System.out.print("Nhập ngày sinh: ");
			int ngaySinh = Integer.parseInt(scanner.nextLine());
//			while (true) {
//				try {
//
//					if (ngaySinh > 0 && ngaySinh <= 31) {
//						break;
//					} else {
//						System.out.print("Ngày sinh không hợp lệ! Vui lòng nhập lại (1-31): ");
//					}
//
//				} catch (Exception e) {
//					System.out.print("Vui lòng nhập số hợp lệ! Nhập lại ngày sinh: ");
//				}
//			}
			System.out.println("Ngày sinh đã nhập: " + ngaySinh);

			System.out.print("Nhập email: ");
			String eMail = scanner.nextLine();
			if (!eMail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
				System.out.println("Email không hợp lệ!");
				return;
			}

			thirdNames.add(new ThirdName(soDienthoai, nhom, hoVaTen, gioiTinh, diaChi, ngaySinh, eMail));
			writeFileThirdName(thirdNames);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateThirdName() {
		Scanner scanner = new Scanner(System.in);
		List<ThirdName> thirdNames = new ArrayList<ThirdName>();
		thirdNames = readFiletThirdNames();
		try {
			System.out.println("Nhập họ và tên để cập nhật: ");
			String hoVaTen = scanner.nextLine();

			for (int i = 0; i < thirdNames.size(); i++) {

				if (hoVaTen != null && hoVaTen.equals(thirdNames.get(i).getHoVaTen())) {
					System.out.println("Nhập số điện thoại mới: ");
					String soDienThoai = scanner.nextLine();
					scanner.nextLine();

					System.out.println("Nhập giới tính:");
					String gioiTinh = scanner.nextLine();

					System.out.print("Nhập địa chỉ: ");
					String diaChi = scanner.nextLine();

					System.out.print("Nhập ngày sinh: ");
					int ngaySinh = scanner.nextInt();
					scanner.nextLine();

					System.out.print("Nhập email: ");
					String email = scanner.nextLine();

					thirdNames.get(i).setSoDienThoai(soDienThoai);
					thirdNames.get(i).setGioiTinh(gioiTinh);
					thirdNames.get(i).setDiaChi(diaChi);
					thirdNames.get(i).setNgaySinh(ngaySinh);
					thirdNames.get(i).setEmail(email);

					System.out.println("Danh bạ cập nhật thành công");
					writeFileThirdName(thirdNames);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteThirdName() {
		Scanner scanner = new Scanner(System.in);
		List<ThirdName> thirdNames = new ArrayList<ThirdName>();
		thirdNames = readFiletThirdNames();

		System.out.print("Nhập số điện thoại cần xóa: ");
		int sdt = scanner.nextInt();
		scanner.nextLine();
		boolean found = false;
		Iterator<ThirdName> iterator = thirdNames.iterator();
		while (iterator.hasNext()) {
			ThirdName thirdName = iterator.next();
			if (thirdName.getSoDienThoai().equals(sdt)) {
				iterator.remove();
				found = true;
				System.out.println("Xóa số điện thoại thành công: " + sdt);
				break;
			}
		}

		if (!found) {
			System.out.println("Không tìm thấy số điện thoại: " + sdt);
		} else {

			try {
				writeFileThirdName(thirdNames);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void searchThirdName() {
		Scanner scanner = new Scanner(System.in);
		List<ThirdName> thirdNames = new ArrayList<ThirdName>();
		thirdNames = readFiletThirdNames();
		System.out.print("Nhập số điện thoại hoặc họ tên cần tìm: ");
		String keyword = scanner.nextLine().toLowerCase();
		List<ThirdName> results = new ArrayList<>();
		for (ThirdName thirdName : thirdNames) {
			if (thirdName.getHoVaTen().toLowerCase().contains(keyword)
					|| thirdName.getSoDienThoai().contains(keyword)) {
				results.add(thirdName);
			}

		}
		if (results.isEmpty()) {
			System.out.println("Không tìm thấy kết quả");
		} else {
			System.out.println("kết quả tìm kiếm: ");
			for (ThirdName result : results) {
				System.out.println(result);
				try {
					writeFileThirdName(thirdNames);
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

	}

	public static void displayThirdName() {
		Scanner scanner = new Scanner(System.in);
		List<ThirdName> thirdName = new ArrayList<ThirdName>();
		thirdName = readFiletThirdNames();

		System.out.println("Hiển thị danh bạ: ");
		if (thirdName.isEmpty()) {
			System.out.println("Danh sách danh bạ rỗng: ");
		} else {
			for (ThirdName thirdName2 : thirdName) {
				System.out.println(thirdName2);
				try {
					writeFileThirdName(thirdName);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
