import styled from 'styled-components';
import Editor, { DiffEditor, useMonaco, loader } from '@monaco-editor/react';
import { useState } from 'react';

// https://www.npmjs.com/package/@monaco-editor/react#installation

function JavaDiffEditor({ setDiffEditor }: any) {
  const [originalJava, setOriginalJava] = useState(`import java.util.Scanner;

  public class SumOfTwoNumbers {
      public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
  
          // Prompt the user to enter the first number
          System.out.print("Enter the first number: ");
          int num1 = scanner.nextInt();
  
          // Prompt the user to enter the second number
          System.out.print("Enter the second number: ");
          int num2 = scanner.nextInt();
  
          // Calculate the sum of the two numbers
          int sum = num1 + num2;
  
          // Display the result
          System.out.println("The sum of " + num1 + " and " + num2 + " is " + sum);
          
          // Close the scanner
          scanner.close();
      }
  }
  `);
  const [greenJava, setGreenJava] = useState(`import java.util.Scanner;

  public class ProductOfTwoNumbers {
      public static void main(String[] args) {
          Scanner scanner = new Scanner(System.in);
  
          // Prompt the user to enter the first number
          System.out.print("Enter the first number: ");
          int num1 = scanner.nextInt();
  
          // Prompt the user to enter the second number
          System.out.print("Enter the second number: ");
          int num2 = scanner.nextInt();
  
          // Calculate the product of the two numbers
          int product = num1 * num2;
  
          // Display the result
          System.out.println("The product of " + num1 + " and " + num2 + " is " + product);
          
          // Close the scanner
          scanner.close();
      }
  }
  `);

  return (
    <StyledDiffEditor>
      <StyledTopWrapper>
        <StyledCodeExplain>
          기존코드/그린코드화 패턴 적용 코드
        </StyledCodeExplain>
        <StyledRunButton
          onClick={() => {
            setDiffEditor(false);
          }}
        >
          코드다시 입력하기
        </StyledRunButton>
      </StyledTopWrapper>
      <div style={{ border: 'solid lightgray 1px' }}>
        <DiffEditor
          height="60vh"
          language="java"
          original={originalJava}
          modified={greenJava}
          theme="light"
          loading="loading.."
        />
      </div>

      <StyledServerInfo>
        <StyledServerWrapper>
          <StyledServerText>서버정보</StyledServerText>
        </StyledServerWrapper>

        <StyledServerWrapper>
          <StyledServerTextsWrapper>
            <StyledServerText>CPU 정보: </StyledServerText>
            <StyledServerText2>AMD 라이젠 12382X12/16코어</StyledServerText2>
          </StyledServerTextsWrapper>
          <StyledServerTextsWrapper>
            <StyledServerText>가용 메모리 크기: </StyledServerText>
            <StyledServerText2>00GB</StyledServerText2>
          </StyledServerTextsWrapper>
          <StyledServerTextsWrapper>
            <StyledServerText>데이터 센터의 에너지 효율성: </StyledServerText>
            <StyledServerText2>1.5</StyledServerText2>
          </StyledServerTextsWrapper>
        </StyledServerWrapper>
      </StyledServerInfo>
    </StyledDiffEditor>
  );
}

export default JavaDiffEditor;

const StyledDiffEditor = styled.div`
  width: 85vw;
  margin: 0 auto;

  @media (max-width: 1280px) {
    width: 95vw;
  }
`;

const StyledTopWrapper = styled.div`
  margin: 10px auto;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 0px 32px;
  height: 35px;
`;

const StyledCodeExplain = styled.p`
  font-family: 'Pretendard';
  font-style: normal;
  font-weight: 600;
  font-size: 20px;
  line-height: 24px;
  color: #3f51b5;
`;

const StyledRunButton = styled.button`
  font-size: 17px;
  font-weight: 600;
  padding: 8px 24px;
  background: #dedeff;
  border-radius: 8px;
  border: none;
`;

const StyledServerInfo = styled.div`
  margin: 10px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  padding: 4px;
  gap: 8px;
  height: 54px;
`;

const StyledServerText = styled.p`
  /* 서버 정보 */
  height: 19px;

  font-family: 'Pretendard';
  font-style: normal;
  font-weight: 600;
  font-size: 16px;
  line-height: 19px;
  color: #000000;
`;

const StyledServerText2 = styled.p`
  height: 19px;
  font-family: 'Pretendard';
  font-style: normal;
  font-weight: 400;
  font-size: 16px;
  line-height: 19px;
  color: #5f6368;
`;

const StyledServerWrapper = styled.div`
  /* Auto layout */
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0px;
  gap: 24px;
  height: 19px;
`;

const StyledServerTextsWrapper = styled.div`
  /* Frame 20 */

  /* Auto layout */
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0px;
  gap: 4px;
  height: 19px;
`;
