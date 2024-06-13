import styled from 'styled-components';
import { ReactComponent as FlightIcon } from '../icons/flight.svg';

type EEIndicatorProps = {
  type: 'flight' | 'train' | 'netflix' | 'google';
  usage: number;
};

function ComparisionIndicator({ type, usage }: EEIndicatorProps) {
  const header = (type: string): { title: string; unit: string } => {
    switch (type) {
      default:
        return { title: '비행 거리', unit: 'gCO2e/km' };
      case 'train':
        return { title: '기차', unit: 'kWh/km' };
      case 'netflix':
        return { title: '넷플릭스', unit: 'kWh' };
      case 'google':
        return { title: '구글 검색', unit: 'kWh' };
    }
  };

  return (
    <CIWrapper>
      <CIHeader>
        {header(type).title}
        <span style={{ color: '#979797' }}>{header(type).unit}</span>
      </CIHeader>
      <CIBody type={type}>
        <FlightIcon />
        {usage.toFixed(2)}
      </CIBody>
    </CIWrapper>
  );
}

// style
const CIWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 4px;
  font-size: 16px;
`;
const CIHeader = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 100%;
  margin: 0;
`;
const CIBody = styled.div<{ type: string }>`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  font-size: 24px;
  border-radius: 16px;
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #3f51b5;
  background-color: white;
`;

export default ComparisionIndicator;
