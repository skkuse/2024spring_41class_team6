
import styled, { keyframes } from 'styled-components';
import { ReactComponent as AirplaneIcon } from '../icons/airplane.svg';

function LoadingAirplane() {
  return (
    <LoadingDotWrapper>
      <LoadingDots />
      <LoadingDots />
      <LoadingDots />
      <AirplaneIcon
        fill="var(--primary-100)"
        width={'128px'}
        transform="rotate(90)"
      />
    </LoadingDotWrapper>
  );
}

//style
const bounceLoader = keyframes`
  to {
    opacity: 0.1;
  }
`;

const LoadingDots = styled.div`
  display: flex;
`;

const LoadingDotWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 16px;

  ${LoadingDots} {
    width: 16px;
    height: 16px;
    margin: 3px 6px;
    border-radius: 50%;
    background-color: var(--primary-100);
    opacity: 1;
    animation: ${bounceLoader} 0.6s infinite alternate;
  }
  ${LoadingDots}:nth-child(2) {
    animation-delay: 0.2s;
  }
  ${LoadingDots}:nth-child(3) {
    animation-delay: 0.4s;
  }
`;

export default LoadingAirplane;
