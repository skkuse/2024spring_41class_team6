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

  return (
    <StdoutContainer>
      <StdoutTitle part={part}>{title}</StdoutTitle>
      <Pre>{stdout}</Pre>
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
  display: flex;
  width: 100%;
  box-sizing: border-box;
  border-radius: 16px;
  padding: 16px 20px;
  white-space: pre-wrap;
  background-color: #f2f2f2;
  font-family: 'Consolas', 'Courier New', monospace;
`;

export default Stdout;
