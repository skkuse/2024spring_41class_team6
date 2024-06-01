import styled from 'styled-components';
import { ReactComponent as GitHubIcon } from '../icons/github-mark.svg';

function GitURLForm() {
  return (
    <GitURLWrapper>
      <Description>
        <StyledH1>GitHub Repository URL을 입력하세요.</StyledH1>
        <StyledP>
          그린화 패턴 적용 후 pull request 해당 repository로 보냅니다.
          Repository는 Public이어야 합니다.
        </StyledP>
      </Description>
      <GitHubIcon />
      <FormWrapper>
        <StyledInput type="url" placeholder="GitHub URL을 입력하세요" />
        <StyledSubmitButton>제출</StyledSubmitButton>
      </FormWrapper>
    </GitURLWrapper>
  );
}

//style
const GitURLWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 36px;
  background: #f1f1f1;
  padding: 32px 48px;
  border-radius: 16px;
  margin-top: 128px;
`;

const StyledH1 = styled.h1`
  font-size: 24px;
  font-weight: bold;
  margin: 0;
  padding: 0;
`;

const Description = styled.div`
  display: flex;
  flex-direction: column;
  gap: 8px;
  align-items: flex-start;
  justify-content: center;
`;

const StyledP = styled.p`
  font-size: 16px;
  margin: 0;
  padding: 0;
  color: #979797;
`;

const FormWrapper = styled.form`
  display: grid;
  grid-template-columns: 1fr auto;
  width: 100%;
  gap: 16px;
  align-items: center;

  @media (max-width: 700px) {
    width: 300px;
    grid-template-columns: 1fr;
    grid-template-rows: 1fr;
    justify-items: center;
  }
`;

const StyledInput = styled.input`
  padding: 8px 16px;
  border-radius: 4px;
  border: var(--bg-300) 1px solid;
  height: auto;
  font-size: 16px;
  width: fill-available;

  &:focus {
    outline: var(--primary-100) auto 1px;
  }

  @media (max-width: 700px) {
    width: 100%;
  }
`;

const StyledSubmitButton = styled.button`
  font-size: 16px;
  width: fit-content;
  color: white;
  padding: 8px 24px;
  background: var(--primary-100);
  border-radius: 8px;
  border: none;
`;

export default GitURLForm;
