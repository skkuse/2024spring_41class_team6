import { useState } from 'react';
import ReactConfetti from 'react-confetti';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

function GitPRDone(props: { url: string }) {
  const width = window.innerWidth;
  const height = window.innerHeight;

  return (
    <>
      <ReactConfetti
        width={width}
        height={height}
        numberOfPieces={500}
        tweenDuration={3000}
        recycle={false}
      />
      <PRWrapper>
        <Emoji>👉</Emoji>
        <StlyedLink to={props.url}>
          PR이 완료되었습니다. 결과를 확인하러 가기<Emoji>🎉</Emoji>
        </StlyedLink>
        <Emoji>👈</Emoji>
      </PRWrapper>
    </>
  );
}

// style
const PRWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  gap: 36px;
  background: #f1f1f1;
  border-radius: 16px;
  padding: 32px 48px;
  margin-top: 128px;
`;

const StlyedLink = styled(Link)`
  text-decoration: none;
  font-weight: 600;
  font-size: 20px;
  margin: 0;
  padding: 0;
`;

const Emoji = styled.span`
  font-size: 20px;
`;
export default GitPRDone;
