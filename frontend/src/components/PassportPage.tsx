import styled from 'styled-components';
import { resultData, comparisionData } from './ExecutionResult';
import Stdout from './Stdout';
import EEIndicator from './EEIndicator';
import ComparisionIndicator from './ComparisionIndicator';

type PassportPageProps = {
  comparision: comparisionData;
  result: resultData;
  part: 'left-page' | 'right-page';
};

function PassportPage({ comparision, result, part }: PassportPageProps) {
  const title = part === 'left-page' ? '기존 코드' : '그린화 패턴 적용 코드';
  const RuntimeInfoItem = (title: string, data: string) => {
    return (
      <div style={{ display: 'flex', gap: '8px' }}>
        <span style={{ fontWeight: 500 }}>{title}:</span>
        <span style={{ color: '#5F6368' }}>{data}</span>
      </div>
    );
  };

  return (
    <Result className={part}>
      <ResultHeader part={part}>{title}</ResultHeader>
      <Line part={part} />
      <Stdout part={part} stdout={result.output} />
      <RuntimeInfo>
        {RuntimeInfoItem('실행 시간', result.runtime + 's')}
        {RuntimeInfoItem('CPU 사용 비율', result.memory + '%')}
        {RuntimeInfoItem('CPU 전력량', result.memory + 'W')}
      </RuntimeInfo>
      <EEContainer>
        <EEIndicator type="co2" usage={result.emission} />
        <EEIndicator type="energy" usage={result.emission} />
      </EEContainer>
      <CIContainer>
        <ComparisionIndicator type="flight" usage={comparision.flight} />
        <ComparisionIndicator type="train" usage={comparision.train} />
        <ComparisionIndicator type="netflix" usage={comparision.netflix} />
        <ComparisionIndicator type="google" usage={comparision.google} />
      </CIContainer>
    </Result>
  );
}

// style
const Result = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin: 0;
  gap: 24px;
  padding: 48px 36px;
  background-color: #fffbf3;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);

  &.left-page {
    border-radius: 0 24px 0 0;
  }

  &.right-page {
    border-radius: 24px 0 0 0;
  }
`;

const ResultHeader = styled.h1<{ part: string }>`
  align-self: ${({ part }) =>
    part === 'left-page' ? 'flex-start;' : 'flex-end;'}
  color: ${({ part }) => (part === 'left-page' ? '#5F6368;' : '#3EAF3F;')}
  margin: 0;
  font-size: 28px;
`;

const Line = styled.div<{ part: string }>`
  width: 400px;
  height: 0.1em;
  background-color: #5f6368;
  align-self: ${({ part }) =>
    part === 'left-page' ? 'flex-start;' : 'flex-end;'};
`;

const RuntimeInfo = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 16px;
  font-size: 16px;
`;
const EEContainer = styled.div`
  display: grid;
  width: 70%;
  grid-template-columns: repeat(2, 1fr);
  gap: 32px;
`;
const CIContainer = styled.div`
  display: grid;
  width: 80%;
  grid-template-columns: repeat(2, 1fr);
  gap: 24px;
  margin: 16px 0;
`;
export default PassportPage;
