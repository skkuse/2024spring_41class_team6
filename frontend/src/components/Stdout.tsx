import styled from 'styled-components';

type StdoutProps = {
  part: 'left-page' | 'right-page';
  stdout: string;
};

function Stdout({ part, stdout }: StdoutProps) {
  const title =
    part === 'left-page'
      ? 'Original Code Output'
      : 'Green Pattern Applied Code Output';

  const outputLimitExceeded =
    '출력 길이 제한을 초과했습니다. 출력이 10KB를 넘으면 일부만 표시됩니다.\n';

  const output =
    stdout.length < 1024 * 10 ? stdout : stdout.slice(0, 1024 * 10) + '...\n';

  return (
    <StdoutContainer>
      <StdoutTitle part={part}>{title}</StdoutTitle>
      <Pre>
        {stdout.length >= 1024 * 10 && (
          <LimitMessage>{outputLimitExceeded}</LimitMessage>
        )}
        {output}
      </Pre>
    </StdoutContainer>
  );
}

const StdoutContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: flex-start;
  font-size: 16px;
  gap: 8px;
`;
const StdoutTitle = styled.div<{ part: string }>`
  font-weight: 500;
  color: ${({ part }) => (part === 'left-page' ? '#5F6368' : '#3EAF3F')};
  align-self: ${({ part }) =>
    part === 'left-page' ? 'flex-start' : 'flex-end'};
`;
const Pre = styled.pre`
  display: block;
  width: 100%;
  box-sizing: border-box;
  border-radius: 16px;
  padding: 16px 20px;
  word-break: break-all;
  white-space: pre-wrap;
  background-color: #f2f2f2;
  font-family: 'Consolas', 'Courier New', monospace;
  overflow-x: auto;
  overflow-y: auto;
  max-height: 400px;
`;
const LimitMessage = styled.div`
  color: #ff0000;
  font-style: italic;
  margin-bottom: 8px;
  font-family: 'Notosans';
`;

export default Stdout;
