import styled from 'styled-components';
import Editor, { DiffEditor, useMonaco, loader } from '@monaco-editor/react';
import sendCode from '../hooks/sendCode';
import { useState, useRef } from 'react';

// https://www.npmjs.com/package/@monaco-editor/react#installation

function JavaDiffEditor({ setDiffEditor }: any) {
  const editorRef = useRef<any | null>(null);

  function handleEditorDidMount(editor: any, monaco: any): void {
    editorRef.current = editor;
  }

  function onSendClick() {
    // let returnValue: any = sendCode(editorRef.current.getValue());
    // returnValue를 redux에 저장
  }

  return (
    <StyledEditor>
      <StyledTopWrapper>
        <StyledCodeExplain>JAVA 코드만 입력해 주세요</StyledCodeExplain>
        <StyledRunButton
          onClick={() => {
            onSendClick();
            setDiffEditor(true);
          }}
        >
          제출
        </StyledRunButton>
      </StyledTopWrapper>
      <div style={{ border: 'solid lightgray 1px' }}>
        <Editor
          height="60vh"
          language="java"
          theme="light"
          loading="loading.."
          onMount={handleEditorDidMount}
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
    </StyledEditor>
  );
}
export default JavaDiffEditor;

const StyledEditor = styled.div`
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
  font-size: 15px;
  font-weight: 600;
  color: white;
  padding: 8px 24px;
  background: #005100;
  border-radius: 8px;
  border: none;
  width: 76px;
  height: 35px;
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
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0px;
  gap: 4px;
  height: 19px;
`;
