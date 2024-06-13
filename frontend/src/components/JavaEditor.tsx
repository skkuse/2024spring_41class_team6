import styled from 'styled-components';
import Editor, { DiffEditor, useMonaco, loader } from '@monaco-editor/react';
import { useDispatch, useSelector } from 'react-redux';
import sendCode from '../hooks/sendCode';
import { useState, useRef, useEffect } from 'react';
import { changeOriginalCode, changeServerResponse } from '../store';
import LoadingAirplane from '../components/LoadingAirplane';
// https://www.npmjs.com/package/@monaco-editor/react#installation

function JavaEditor({ setDiffEditor }: any) {
  let dispatch = useDispatch();

  const [isLoading, setIsLoading] = useState<boolean>(false);
  const editorValueRef = useRef<string | undefined>('');
  const [editorByte, setEditorByte] = useState<number>(0);
  const editorKb = (editorByte / 1024).toFixed(2);
  const [showWarning, setShowWarning] = useState(false);
  const [hasError, setHasError] = useState(false);
  const [stderrMessage, setStderrMessage] = useState('');

  function handleEditorChange(value: string | undefined, event: any) {
    editorValueRef.current = value;
    updateEditorByte(value);
  }

  const updateEditorByte = (value: string | undefined) => {
    let byteLength = new TextEncoder().encode(value).length;
    setEditorByte(byteLength);
  };

  async function onSendClick() {
    if (editorByte < 64 * 1024 && editorByte > 0) {
      try {
        setIsLoading(true);
        let returnValue: any = await sendCode(editorValueRef.current, dispatch);
        if (returnValue.stderr) {
          setHasError(true);
          setStderrMessage(returnValue.stderr);
        } else {
          setHasError(false);
          setStderrMessage('');
          dispatch(changeOriginalCode(editorValueRef.current));
          dispatch(changeServerResponse(returnValue));
          dispatch;
          setDiffEditor(true);
        }
        setIsLoading(false);
      } catch (error) {
        setHasError(true);
        setStderrMessage('' + error);
      }
    } else {
      setShowWarning(true);
      setTimeout(() => setShowWarning(false), 4000); // Hide warning after 4 seconds
    }
  }
  if (isLoading) {
    return <LoadingAirplane />;
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

        {hasError ? (
          <ErrorWrapper>
            <Editor
              height="60vh"
              language="java"
              theme="light"
              loading="loading.."
              onChange={handleEditorChange}
            />
          </ErrorWrapper>
        ) : (
          <div>
            <Editor
              height="60vh"
              language="java"
              theme="light"
              loading="loading.."
              onChange={handleEditorChange}
            />
          </div>
        )}
        {hasError && <StderrMessage>{stderrMessage}</StderrMessage>}
      </div>

      <StyledServerInfo>
        <StyledServerWrapper>
          <StyledServerText2>Editor: {editorKb}KB</StyledServerText2>
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
      {showWarning && (
        <WarningMessage>
          0 이상 64KB 이하의 자바코드만 입력가능합니다.
        </WarningMessage>
      )}
    </StyledEditor>
  );
}
export default JavaEditor;

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

const ErrorWrapper = styled.div`
  border: solid red 2px;
  opacity: 0.8;
`;

const StderrMessage = styled.div`
  color: red;
  margin-top: 10px;
`;

const WarningMessage = styled.div`
  background-color: #cc3300;
  color: white;
  padding: 10px;
  border-radius: 5px;
  position: fixed;
  top: 20px;
  right: 20px;
  animation: fadeInOut 4s ease-in-out;

  @keyframes fadeInOut {
    0% {
      opacity: 0;
      transform: translateY(-20px);
    }
    10% {
      opacity: 1;
      transform: translateY(0);
    }
    90% {
      opacity: 1;
      transform: translateY(0);
    }
    100% {
      opacity: 0;
      transform: translateY(-20px);
    }
  }
`;
