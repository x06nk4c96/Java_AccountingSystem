import java.util.Scanner;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountManager manager = new AccountManager();
        manager.loadFromFile();  // 啟動時從檔案讀取紀錄

        while (true) {
            System.out.println("1.新增 2.查看 3.總額 4.分類查詢 5.時間分析 6.離開");
            
            if (!scanner.hasNextInt()) {
                System.out.println("請輸入有效的選項");
                scanner.next();  // 清除無效輸入
                continue;
            }
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    LocalDate date;

                    while (true) {
                        System.out.print("輸入日期 (yyyy-mm-dd) : ");
                        String dateInput = scanner.nextLine();
                    
                        try {
                            date = LocalDate.parse(dateInput);  // 解析日期輸入
                            break;  // 日期格式正確，跳出循環
                        } catch (Exception e) {
                            System.out.println("格式錯誤!請輸入 yyyy-mm-dd");
                        }
                    }

                    String type;
                    while (true) {
                        System.out.print("輸入類型 (income/expense) : ");
                        type = scanner.nextLine();
                        
                        if (type.equals("income") || type.equals("expense")) {
                            break;
                        } else {
                            System.out.println("請輸入 'income' 或 'expense'");
                        }
                    }

                    String category = "";
                    if (type.equals("expense")) {
                        while (true) { 
                             System.out.print("輸入類別 (food/transport/fun) : ");
                            category = scanner.nextLine();
                            if (category.equals("food") || category.equals("transport") || category.equals("fun")) {
                            break;
                            } else {
                            System.out.println("請輸入正確類別");
                            }
                        }
                    }    

                    double amount;
                    while (true) {
                        System.out.print("輸入金額 : ");
                        if (scanner.hasNextDouble()) {
                            amount = scanner.nextDouble();
                            scanner.nextLine();
                            break;
                        } else {
                            System.out.println("請輸入數字格式的金額");
                            scanner.next();  // 清除無效輸入
                        }
                    }

                    System.out.print("輸入描述 : ");
                    String description = scanner.nextLine();
                    
                    Transaction t = new Transaction(amount, type, category, description, date);
                    manager.addTransaction(t);
                    break;
                
                case 2:
                    manager.showAll();
                    break;
                
                case 3:
                    double balance = manager.getBalance();
                    System.out.println("總額 : " + balance);
                    break;

                case 4:
                    while (true) {
                        System.out.println("\n--- 分類分析 ---");
                        System.out.println("1. 查詢單一分類 2. 分類排行榜 3. 返回主選單");

                        if (!scanner.hasNext()) {
                            System.err.println("請輸入有效選項");
                            scanner.next();  // 清除無效輸入
                            continue;
                        }

                        int subChoice = scanner.nextInt();
                        scanner.nextLine();  // 清除換行符

                        switch(subChoice) {
                            case 1:
                                System.out.println("輸入要查詢的分類 (food/transport/fun) : ");
                                String cat = scanner.nextLine();

                                double total = manager.getCategoryExpense(cat);
                                System.out.println(cat + " 類別總額 : " + total);
                                break;

                            case 2:
                                manager.showCategorySummary();
                                break;

                            case 3:
                                System.out.println("返回主選單");
                                break;

                            default:
                                System.out.println("無效選項，請重新輸入");
                        }

                        //跳出子選單
                        if (subChoice == 3) {
                            break;
                        }
                    }
                    break;

                case 5:
                    while (true) {
                        System.out.println("\n--- 時間分析 ---");
                        System.out.println("1. 本月總支出 2. 本月分類排行 3. 返回主選單");

                        if (!scanner.hasNextInt()) {
                            System.err.println("請輸入有效選項");
                            scanner.next();  // 清除無效輸入
                            continue;
                        }

                        int sub = scanner.nextInt();
                        scanner.nextLine();  // 清除換行符

                        switch (sub) {
                            case 1:
                                double total = manager.getMonthlyExpense();
                                System.out.println("本月總支出 : " + total);
                                break;

                            case 2:
                                manager.showMonthlyCategorySummary();
                                break;

                            case 3:
                                System.out.println("返回主選單");
                                break;

                            default:
                                System.out.println("無效選項，請重新輸入");
                        }

                        if (sub == 3)
                        break;
                    }
                    break;

            
                case 6:
                    manager.saveToFile();  // 離開前儲存紀錄到檔案
                    System.out.println("離開程式");
                    return;
            }
            System.out.println("---------------------------------");  // 增加空行以提升可讀性
        }
    }
    
}
