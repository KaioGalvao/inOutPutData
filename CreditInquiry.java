//package inOutPutData;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

public class CreditInquiry {
    private MenuOption accountType;
    private Scanner input;
    private MenuOption choices[] = {MenuOption.zero_balance, MenuOption.credito_balance, MenuOption.debito_balance, MenuOption.end};

    private void readRecords() throws IOException{
        AccountRecord record = new AccountRecord();
        try {
            File file = new File("cliente.txt");
            file.createNewFile();
            input = new Scanner(file);
            
            while (input.hasNext()) {
                record.setAccount(input.nextInt());
                record.setFirstName(input.next());
                record.setLastName(input.next());
                record.setBalance(Double.parseDouble(input.next()));
                if(shouldDisplay(record.getBalance())) {
                    System.out.printf("%-10d%-12s%-12s%10.2f\n", record.getAccount(), record.getFirstName(), record.getLastName(), record.getBalance());
                }
            }
        } catch (NoSuchElementException elemException) {
            System.out.println("erro no arquivo!");
            input.close();
            System.exit(1);
        }
        catch(IllegalStateException stateException) {
            System.out.println("arquivo não encontrado");
            System.exit(1);
        }
        finally {
            if(input != null)
                input.close();
        }
    }

    private boolean shouldDisplay(double balance) {
        if((accountType == MenuOption.credito_balance) && (balance<0))
            return true;
        else if((accountType == MenuOption.debito_balance) && (balance>0))
            return true;
        else if((accountType == MenuOption.zero_balance) && (balance == 0))
            return true;
        return false;
    }

    private MenuOption getRequest(Scanner textIn) {
        int request = 1;
        System.out.printf("\n%s\n%s\n%s\n%s\n%s\n", "Enter request", "1 - Lista contas com zero", "2 - Lista contas com credito", "3 - Lista contas com debito", "4 - fim");
        try {
            do {
                System.out.print("\n?");
                request = textIn.nextInt();
            }
            while((request < 1) || (request > 4));
        } catch (NoSuchElementException elem) {
            System.out.println("Entrada inválida");
            System.exit(1);
        }
        return choices[request - 1];
    }

    public void processRequest() throws IOException {
        Scanner textIn = new Scanner(System.in);
        while (accountType != MenuOption.end) {
            accountType = getRequest(textIn);
            switch (accountType) {
                case zero_balance:
                    System.out.println("\nConta com saldo zero:\n");
                    break;
                case credito_balance:
                    System.out.println("\nConta com credito:\n");
                    break;
                case debito_balance:
                    System.out.println("Conta  com debito:\n");
                    break;
                default: 
                    break;
            }
            readRecords();
        }
        textIn.close();
    }
}